import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './components/app/App';
import { BrowserRouter, Route, Routes, useRoutes } from 'react-router-dom';
import Stats from './components/stats/Stats';



ReactDOM.render(
    <BrowserRouter>
        <Routes>
            <Route path="/" element={ <App/> }/>
            <Route path="/stats" element={ <Stats/> } />
        </Routes>
    </BrowserRouter>,
    document.getElementById('root')
);

