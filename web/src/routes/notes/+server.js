import { json } from "@sveltejs/kit";
import { NOTES_SECRET_KEY } from "$env/static/private";
import { deleteNotes, getNotes, insertNotes } from "./notes_api.js";

/**
 * @typedef {Object} Note
 * @property {string} id
 * @property {number} time_posted
 * @property {string} text
 * @property {string} owner
 * @property {string} color
 */

/**
 * Checks to see if the call is authorized
 * @param {{ headers: Map<String, String> }} request
 * @returns {boolean}
 */
const checkAPIKey = (request) => {
  return request.headers.get("x-api-key") === NOTES_SECRET_KEY;
};

const unauthorized = () =>
  new Response(JSON.stringify({ error: "unauthorized" }), {
    status: 401,
    headers: { "Content-Type": "application/json" },
  });

const fail = (message) =>
  new Response(JSON.stringify({ error: message }), {
    status: 400,
    headers: { "Content-Type": "application/json" },
  });

/**
 * @param {Record<string, Note>} notes
 */
const response = (notes) => json({ notes });

export async function GET({ request }) {
  if (!checkAPIKey(request)) return unauthorized();
  const notes = await getNotes();
  return response(notes);
}

/**
 * @param {{ request: { json: () => {notes: Note[], action: string} } }} data
 */
export async function POST({ request }) {
  if (!checkAPIKey(request)) return unauthorized();
  const payload = await request.json();
  if (payload.notes) {
    for (const note of payload.notes) {
      if (!note.id || !note.text || !note.time_posted)
        return fail("invalid payload");
    }
    console.log(payload.action, payload.notes);
    const actions = {
      add: async (payload_notes) => {
        await insertNotes(payload_notes);
      },
      remove: async (payload_notes) => {
        await deleteNotes(payload_notes);
      },
    };
    const action = actions[payload.action];
    if (!action) return fail("unknown action");
    await action(payload.notes);
    const notes = await getNotes();
    return response(notes);
  } else {
    return fail("notes is required");
  }
}
