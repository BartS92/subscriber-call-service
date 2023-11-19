package subscriber.call.group.bff.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import subscriber.call.group.bff.dto.EventResponseDto;
import subscriber.call.group.bff.dto.stats.CallCountDto;
import subscriber.call.group.bff.dto.stats.CallInfoDto;
import subscriber.call.group.bff.dto.stats.StatsDto;
import subscriber.call.group.bff.dto.stats.TimeDataDto;

public class StatsUtils {

    private static final String CALL_STARTED_STATUS = "STARTED";
    private static final String NOT_FOUND_STATUS = "NOT_FOUND";
    private static final String CALL_FINISHED_STATUS = "FINISHED";

    public static StatsDto createStats (EventResponseDto[]  events) {
        var statsDto = new StatsDto();
        statsDto.setCallInfo(getCallInfo(events));

        statsDto.setTotalCount(getTotalCount(events));
        statsDto.setFailedCount(getFailedCount(events));

        statsDto.setTotalTime(getTotalTime(events));
        statsDto.setPopularPhoneByTotalTime(getPopularPhoneByTotalTime(events));

        statsDto.setPopularPhonesByCallCount(getPopularPhoneByCallCount(events));
        statsDto.setPopularPhonesByCallDuration(getPopularPhoneByCallDuration(events));
        return  statsDto;
    }

    private static long getTotalCount(EventResponseDto[] events) {
        return Arrays.stream(events)
                .filter(event -> event.getStatus().equals(CALL_STARTED_STATUS) || event.getStatus().equals(NOT_FOUND_STATUS))
                .count();
    }

    private static long getFailedCount(EventResponseDto[] events) {
        return  Arrays.stream(events)
                .filter(event -> event.getStatus().equals(NOT_FOUND_STATUS))
                .count();
    }

    private static long getTotalTime(EventResponseDto[] events) {
        var filtered = new ArrayList<EventResponseDto>();

        Collections.addAll(filtered, Arrays.stream(events)
                .filter(event -> event.getStatus().equals(CALL_STARTED_STATUS) || event.getStatus().equals(CALL_FINISHED_STATUS)).toArray(EventResponseDto[]::new));

        long time = 0;

        while (!filtered.isEmpty()) {
            var started = filtered.stream().filter(event -> event.getStatus().equals(CALL_STARTED_STATUS)).findFirst().get();
            var finished = filtered.stream()
                    .filter(event -> event.getInitPhone() ==  started.getInitPhone() && event.getReceivingPhone() == started.getReceivingPhone() && event.getStatus().equals(CALL_FINISHED_STATUS))
                    .findFirst();

            filtered.removeIf(e -> e.getId() == started.getId()) ;
            if (finished.isEmpty()) {
                continue;
            }

            time += getDurationInSeconds(finished.get().getDateCreated().getTime(), started.getDateCreated().getTime());

            filtered.removeIf(e -> e.getId() == finished.get().getId());
        }

        return time;
    }

    private static List<CallInfoDto> getCallInfo(EventResponseDto[] events) {
        var eventsArray = new ArrayList<EventResponseDto>();
        Collections.addAll(eventsArray, events);
        var actualCalls = eventsArray.stream().filter(event -> event.getStatus().equals(CALL_STARTED_STATUS) || event.getStatus().equals(CALL_FINISHED_STATUS)).toList();

        var statsRows = new ArrayList<CallInfoDto>();

        statsRows.addAll(getPhonesCallInfo(actualCalls.stream().map(EventResponseDto::getInitPhone).distinct().toList(), events, false));
        statsRows.addAll(getPhonesCallInfo(actualCalls.stream().map(EventResponseDto::getReceivingPhone).distinct().toList(), events, true));

        return statsRows;
    }


    private static ArrayList<CallInfoDto> getPhonesCallInfo(List<Long> phones, EventResponseDto[] events, boolean isPhonesReceivings) {
        var actualCalls = Arrays.stream(events).filter(event -> event.getStatus().equals(CALL_STARTED_STATUS) || event.getStatus().equals(CALL_FINISHED_STATUS)).toList();
        var statsRows = new ArrayList<CallInfoDto>();
        for (long phone : phones) {
            var row = new CallInfoDto();
            row.setInitPhone(phone);

            List<Long> receivings;
            var initCalls =  Arrays.stream(events)
                    .filter(e -> e.getStatus().equals(CALL_STARTED_STATUS) || e.getStatus().equals(CALL_FINISHED_STATUS));
            if (isPhonesReceivings) {
                receivings = initCalls.filter(e -> e.getReceivingPhone() == phone).map(initCall -> initCall.getInitPhone()).distinct().toList();
            }
            else {
                receivings = initCalls.filter(e -> e.getInitPhone() == phone).map(initCall -> initCall.getReceivingPhone()).distinct().toList();
            }

            var callInfo = new HashMap<Long, TimeDataDto>();
            for (long receiving : receivings) {
                var timeData = new TimeDataDto();
                timeData.setIncomingTime(getCallTotalDuration(phone, receiving, actualCalls));
                timeData.setOutgoingTime(getCallTotalDuration(receiving, phone, actualCalls));

                callInfo.put(receiving, timeData);
                row.setInfo(callInfo);
            }
            statsRows.add(row);
        }

        return statsRows;
    }

    private static CallCountDto getPopularPhoneByCallCount(EventResponseDto[] events) {
        var phoneCallCountMap = getCallCountMap(events);

        long phone = 0;
        Integer callCount = 0;
        for (long key: phoneCallCountMap.keySet()){
            if (phoneCallCountMap.get(key) > callCount) {
                phone = key;
                callCount = phoneCallCountMap.get(key);
            }
        }

        return new CallCountDto(phone, callCount);
    }

    private static  Map<Long, Integer> getCallCountMap(EventResponseDto[] events){
        var phoneCallCountMap = new HashMap<Long, Integer>();
        var receivingPhones = Arrays.stream(events).filter(event -> event.getStatus().equals(CALL_STARTED_STATUS)).map(e -> e.getReceivingPhone()).toList();
        for(long phone: receivingPhones){
            if (phoneCallCountMap.get(phone) == null){
                phoneCallCountMap.put(phone, 1);
            }
            else {
                var count = phoneCallCountMap.get(phone);
                phoneCallCountMap.put(phone, count + 1);
            }
        }

        return phoneCallCountMap;
    }

    private static Map<Long, Long> getPhoneCallTime(EventResponseDto[] events) {
        var phoneCallDuration = new HashMap<Long, Long>();
        var actualCalls = Arrays.stream(events)
                .filter(event -> event.getStatus().equals(CALL_STARTED_STATUS) || event.getStatus().equals(CALL_FINISHED_STATUS)).toList();
        var receivingPhones = actualCalls.stream()
                .map(e -> e.getReceivingPhone())
                .distinct()
                .toList();
        for (var receivingPhone : receivingPhones) {
            long time = 0;
            var initPhones = actualCalls.stream().filter(call -> call.getReceivingPhone() == receivingPhone).map(e -> e.getInitPhone()).distinct().toList();
            for (var intPhone : initPhones) {
                time += getCallTotalDuration(intPhone, receivingPhone, actualCalls);
                phoneCallDuration.put(receivingPhone, time);
            }
        }

        return phoneCallDuration;
    }


    private static CallCountDto getPopularPhoneByCallDuration(EventResponseDto[] events) {
        var callCountMap = getCallCountMap(events);
        var callTimeMap = getPhoneCallTime(events);

        var popularPhone = 0l;
        var duration = 0l;
        for (var phone : callTimeMap.keySet()) {
            if (callTimeMap.get(phone) / callCountMap.get(phone) > duration) {
                duration = callTimeMap.get(phone) / callCountMap.get(phone);
                popularPhone = phone;
            }

        }

        return new CallCountDto(popularPhone, duration);
    }

    private static CallCountDto getPopularPhoneByTotalTime(EventResponseDto[] events) {
        var phoneCallDuration = getPhoneCallTime(events);

        var bigDuration = 0l;
        var popularPhone = 0l;
        for (var phone: phoneCallDuration.keySet()){
            if (phoneCallDuration.get(phone) > bigDuration){
                bigDuration = phoneCallDuration.get(phone);
                popularPhone = phone;
            }
        }

        return new CallCountDto(popularPhone, bigDuration);
    }


    private static long getCallTotalDuration(long initPhone, long receivingPhone, List<EventResponseDto> events) {
        var calls = events.stream().filter(e -> e.getInitPhone() == initPhone && e.getReceivingPhone() == receivingPhone && (e.getStatus().equals(CALL_STARTED_STATUS) || e.getStatus().equals(CALL_FINISHED_STATUS))).toList();
        var callsArray = new ArrayList<>(calls);

        var time = 0;
        while (callsArray.size() > 0) {
            var started = callsArray.stream().filter(e -> e.getStatus().equals(CALL_STARTED_STATUS)).findFirst();
            var finished = callsArray.stream().filter(e -> e.getStatus().equals(CALL_FINISHED_STATUS)).findFirst();
            callsArray.removeIf(c -> c.getId() == started.get().getId());
            if (!finished.isPresent()) {
                continue;
            }

            time += getDurationInSeconds(finished.get().getDateCreated().getTime(), started.get().getDateCreated().getTime());

            callsArray.removeIf(c -> c.getId() == finished.get().getId());
        }

        return time;
    }

    private static long getDurationInSeconds(long time1, long time2){
        long diff = time1 - time2;
        var timeUnit = TimeUnit.SECONDS;
        return timeUnit.convert(diff, TimeUnit.MILLISECONDS);
    }

}
