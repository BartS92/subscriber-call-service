import './Stats.css';
import { useEffect, useState } from 'react';
import { CallInfo, getStats, StatResponse, TimeDataDto } from '../../store/apis/baseApi';

function Stats() {
  const [stats, setStats] = useState<StatResponse | null>(null);

  useEffect(() => {
    getStats(onGetStatsHandler)
  }, []);

  const onGetStatsHandler = (data: StatResponse) => {
    setStats(data);
  }

  const createInfoRows = (infoRow: CallInfo) => {
    const phones = Object.keys(infoRow.info);
    return <>
      { phones.map((phone, i) => i === 0 && createInfoWithInitPhone(infoRow.initPhone, parseInt(phone), infoRow.info[parseInt(phone)]!) ||  createInfoRow(parseInt(phone), infoRow.info[parseInt(phone)]!)) }
    </>
  }

  const createInfoRow = (phone: number, timeData: TimeDataDto) => {
    return <>
      <div className={ 'row' }>
        <div></div>
        <div>{ phone }</div>
          <div className={'dash'}> - </div>
        <div>({ `${ timeData?.incomingTime }sec+${ timeData?.outgoingTime }sec` })</div>
      </div>
    </>
  }

  const createInfoWithInitPhone = (initPhone: number, receivingPhone: number, timeData: TimeDataDto) => {
    return <>
      <div className={ 'row' }>
        <div>{ initPhone }</div>
        <div>{ receivingPhone }</div>
        <div className={'dash'}> - </div>
        <div>({ `${ timeData?.incomingTime }sec+${ timeData?.outgoingTime }sec` })</div>
      </div>
    </>
  }


  return (
    <div className={'stats'}>
      <div className={'table'}>
          <div className={'header'}>Call info:</div>
        {stats?.callInfo.map(info => createInfoRows(info))}
      </div>

       <div className={'info'}>
           <div>
               Failed call count: {stats?.failedCount}
           </div>

           <div>
               Total call count: {stats?.totalCount}
           </div>

           <div>
               Total call time: {stats?.totalTime} sec
           </div>

           <div>
               Popular phone by call count: {stats?.popularPhonesByCallCount.phone} ({stats?.popularPhonesByCallCount.count})
           </div>

           <div>
               Popular phone by call duration: {stats?.popularPhonesByCallDuration.phone} (average: {stats?.popularPhonesByCallDuration.count} sec)
           </div>

           <div>
               Popular phone by total time: {stats?.popularPhoneByTotalTime.phone} ({stats?.popularPhoneByTotalTime.count} sec)
           </div>
       </div>
    </div>
  );
}

export default Stats;
