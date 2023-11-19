import $ from 'jquery';

// const HOST= 'localhost:8080'
const SERVICE_API = '/subscriber-call-app/service'

// const URL = HOST + SERVICE_API;

export type CallResponse = {
     status: string;
     message: string
}

export type CallCount = {
     phone: number;
     count: number;
}

export type CallInfo = {
    initPhone: number;
    info: Array<TimeDataDto>;
}

export type TimeDataDto = {
    incomingTime: number;
    outgoingTime: number;
}

export type StatResponse = {
    callInfo: CallInfo[];

    totalCount: number;
    failedCount: number;
    totalTime: number;

    popularPhonesByCallCount: CallCount;
    popularPhonesByCallDuration: CallCount;
    popularPhoneByTotalTime: CallCount;
}

export const startCall = (initPhone: number, receivingPhone: number, onSetStatus: (status: CallResponse) => void) => {
    $.post(`${SERVICE_API}/start-call/from/${initPhone}/to/${receivingPhone}`, (response: CallResponse) => {
        onSetStatus(response);
    })
}

export const finishCall = (initPhone: number, receivingPhone: number, onSetStatus: (status: CallResponse) => void) => {
    $.post(`${SERVICE_API}/finish-call/from/${initPhone}/to/${receivingPhone}`, (response: CallResponse) => {
        onSetStatus(response);
    })
}

export const getStats = (onGetStats: (data: StatResponse) => void) => {
    $.get(`${SERVICE_API}/stats`, (data: StatResponse) => onGetStats(data));
}