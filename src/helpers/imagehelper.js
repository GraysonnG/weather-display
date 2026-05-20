import fs from 'fs';
import path from 'path';

export const loadImage = (relativePathFromStatic) => {
    const filePath = path.resolve('static', relativePathFromStatic)
    const ext = relativePathFromStatic.split('.')[1]
    const mimeType = {
        "png": "image/png",
        "jpg": "image/jpeg",
        "svg": "image/svg+xml",
    }[ext]
    const data = fs.readFileSync(filePath)
    const base64 = Buffer.from(data).toString('base64')
    return `data:${mimeType};base64,${base64}`
}