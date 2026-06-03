import satori from "satori";
import { Resvg } from "@resvg/resvg-js";
import { Cat } from "./cat.jsx";
import { createImageResponse } from "../../helpers/image_endpoint_helper.js";

export async function GET() {
  return createImageResponse(Cat());
}
