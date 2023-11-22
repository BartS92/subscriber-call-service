import './App.css';
import { CallResponse, finishCall, startCall } from '../../store/apis/baseApi';
import { useState } from 'react';

function App() {
    const [initPhone, setInitPhone] = useState("")
    const [receivingPhone, setReceivingPhone] = useState("")

    const [status, setStatus] = useState<CallResponse>({status: "", message: ""})

    const [error, setError] = useState('')

    const [callProgress, setCallProgress] = useState(false)

    const checkCorrectNumber =  (phone: string) =>  {
        if (phone.trim().length > 6){
            setError(`Error: Phone  ${ phone.trim() } contains more than 6 numbers`)
            return false;
        }
        return true;
    }

    const onClick = (callFunc: any) => {
        if (checkCorrectNumber(initPhone) && checkCorrectNumber(receivingPhone)) {
            setError("");
            callFunc(parseInt(initPhone), parseInt(receivingPhone), (response: CallResponse) => {
                setStatus(response);
                setCallProgress(response.status === "STARTED")
            })
        }
    }

  return (
    <div className={'app'} style={{width: 500}}>
        <div className={ 'call' }>
            <div className={ 'numbers' }>
                <input placeholder={ 'Your phone number' } value={ initPhone } disabled={callProgress} onChange={ (e) => {
                    setInitPhone(e.currentTarget.value)
                } }/>
                <input placeholder={ 'Friends phone number' } value={ receivingPhone } disabled={callProgress} onChange={ (e) => {
                    setReceivingPhone(e.currentTarget.value)
                } }/>
            </div>
            <button className={ 'call-button' } disabled={ (initPhone.trim().length === 0 || receivingPhone.trim().length == 0) || callProgress }
                    onClick={ () => onClick(startCall) }>Make a call
            </button>
            <button className={ 'finish-button' } disabled={ !callProgress }
                    onClick={ () => onClick(finishCall) }>Finish call
            </button>
        </div>
        <div className={'notification'}>
            <p>{status.message}</p>
            <p className={'error'}>{error}</p>
        </div>
    </div>
  );
}

export default App;
