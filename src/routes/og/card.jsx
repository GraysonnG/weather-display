/** @jsxRuntime classic */
/** @jsx h */
import { h, Fragment } from '../../lib/jsx.js';

export function Card({ title }) {
    return (
        <div style={{
            display: 'flex',
            background: '#f06',
            width: '100%',
            height: '100%',
            alignItems: 'center',
            justifyContent: 'center',
            fontSize: 64,
            color: 'white',
        }}>
            { title }
        </div>
    );
}
