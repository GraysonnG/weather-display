/** @jsxRuntime classic */
/** @jsx h */
import { h, Fragment } from '../../../lib/jsx.js';

const createTempLinePath = (temps, height, width = 200, padding = 20) => {
    const viewboxWidth = width
    const min = Math.min(...temps)
    const max = Math.max(...temps)
    const range = max - min || 1;

    const points = temps.map((temp, i) => {
        const x = padding + (i / (temps.length - 1)) * (viewboxWidth - padding * 2)
        const y = padding + (1 - (temp - min) / range) * (height - padding * 2)
        return { x, y }
    })

    const path = points
        .map((point, i) => `${i === 0 ? 'M' : 'L'} ${point.x.toFixed(1)},${point.y.toFixed(1)}`)
        .join(' ')

    return { path, points, viewboxWidth }
}

export const TempChart = ({temps}) => {
    const height = 160
    const width = 1200
    const padding = 10
    const { path, points, viewboxWidth } = createTempLinePath(temps, height, width, padding)
    
    return (<svg
        style={{
            display: 'flex',
            position: 'absolute',
            left: '3.25%',
            bottom: '-8',
            width: '93.6%',
            height,
            opacity: 1,
        }}
        height={height}
        viewBox={`0 0 ${viewboxWidth} ${height}`}
        preserveAspectRatio="none"
    >
        <path d={path} fill="none" stroke="#5080b8" stroke-width="2" stroke-dasharray="8" />
        {points.map((p, i) => (
            <circle key={i} cx={p.x} cy={p.y} r="8" fill="#5080b8" />
        ))}
    </svg>)
}