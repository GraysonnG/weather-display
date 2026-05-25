/** @jsxRuntime classic */
/** @jsx h */
import { loadImage } from '../../../helpers/imagehelper.js';
import { h, Fragment } from '../../../lib/jsx.js';
import { TodaySection } from './today.jsx';
import { DailySection } from './daily.jsx';

const wrapperStyle = {
    display: 'flex',
    position: 'relative',
    background: '#fff',
    width: '100%',
    height: '100%',
    alignItems: 'center',
    justifyContent: 'center',
    fontSize: 12,
    color: 'white',
    padding: 15,
}

const layoutStyle = {
    color: '#000',
    display: 'flex',
    flexDirection: 'column',
    width: '100%',
    height: '100%',
    padding: 15,
    borderRadius: 15,
    border: '2px solid #000',
    background: '#fff2'
}

const imgStyle = {
    position: 'absolute',
    top: 0,
    left: 0,
    width: '100%',
    height: '100%',
    objectFit: 'cover',
    filter: 'blur(15)',
}

export const Layout = (data) => (<div style={wrapperStyle}>
    <img style={imgStyle} src={loadImage("backgrounds/cloudy.jpg")} />
    <div style={layoutStyle}>
        <TodaySection currentData={data.current} hourlyData={data.hourly}/>
        <DailySection data={data.daily} />
    </div>
</div>)
