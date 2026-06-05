import fs from "fs";
import path from "path";
import { dev } from "$app/environment";

export const getAllImages = (relativePathFromStatic) => {
  const dirPath = dev
    ? path.resolve("static", relativePathFromStatic)
    : path.resolve("static", relativePathFromStatic);
  const fileNames = fs.readdirSync(dirPath);
  return fileNames.map((name) => {
    const filePath = path.resolve(dirPath, name);
    const split = name.split(".");
    const ext = split[split.length - 1];
    const mimeType = {
      png: "image/png",
      jpg: "image/jpeg",
      svg: "image/svg+xml",
    }[ext];
    const data = fs.readFileSync(filePath);
    const base64 = Buffer.from(data).toString("base64");
    return `data:${mimeType};base64,${base64}`;
  });
};
