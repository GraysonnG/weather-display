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

export const getAllImages = (relativePathFromStatic) => {
    const dirPath = path.resolve('static', relativePathFromStatic)
    const fileNames = fs.readdirSync(dirPath)
    return fileNames.map(name => {
        const filePath = path.resolve(dirPath, name)
        const ext = name.split('.')[1]
        const mimeType = {
            "png": "image/png",
            "jpg": "image/jpeg",
            "svg": "image/svg+xml",
        }[ext]
        const data = fs.readFileSync(filePath)
        const base64 = Buffer.from(data).toString('base64')
        return `data:${mimeType};base64,${base64}`
    })
}
