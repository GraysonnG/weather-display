import { getStore } from "@netlify/blobs";
import { json } from "@sveltejs/kit";
import { NOTES_SECRET_KEY } from "$env/static/private";

const STORE_KEY = "notes";
const CACHE_KEY = "cache";

let store;

/**
 * @typedef {Object} Note
 * @property {string} id
 * @property {number} time_posted
 * @property {string} text
 * @property {string} owner
 * @property {string} color
 */

/**
 * @typedef {Object} Payload
 * @property {string} action
 * @property {Note[]} notes
 */

const getNoteStore = () => {
  if (!store) store = getStore(STORE_KEY);
  return store;
};

/**
 * @returns {Array<Note>}
 */
const getNotes = async () => {
  const store = getNoteStore();
  const cache = await store.get(CACHE_KEY, { type: "json" });
  if (!cache || !cache.notes) {
    console.log("Initializing Notes: {}");
    setNotes({});
    return {};
  }
  return cache.notes;
};
// (await getNoteStore().get(CACHE_KEY, { type: "json" })).notes;

const setNotes = async (notes) => {
  await getNoteStore().set(
    CACHE_KEY,
    JSON.stringify({
      notes: notes,
    }),
  );
};

/**
 *
 * @param {Note[]} payload_notes notes from the payload
 * @param {Record<string, Note>} store_notes notes from the netlify store
 * @returns {Record<string, Note>}
 */
const addNotes = (payload_notes, store_notes) => {
  const map = { ...store_notes };

  payload_notes.forEach((note) => {
    map[note.id] = note;
  });

  return map;
};

/**
 *
 * @param {Note[]} payload_notes notes from the payload
 * @param {Record<string, Note>} store_notes notes from the netlify store
 * @returns {Record<string, Note>}
 */
const removeNotes = (payload_notes, store_notes) => {
  const map = { ...store_notes };

  payload_notes.forEach((note) => {
    delete map[note.id];
  });

  return map;
};

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
 *
 * @param {Record<string, Note>} notes
 * @returns
 */
const response = (notes) => json({ notes });

export async function GET({ request }) {
  if (!checkAPIKey(request)) return unauthorized();

  const notes = await getNotes();

  if (notes == null) {
    await setNotes([]);
    return json([]);
  }

  return response(notes);
}

/**
 * Accepts a {Note} and adds it to the note store and then returns the updated note list.
 * @param {{ request: { json: () => {notes: Note[]} } }} data
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
      add: addNotes,
      remove: removeNotes,
    };
    const action = actions[payload.action];
    if (!action) return fail("unknown action");

    const notes = await getNotes();
    const updatedNotes = action(payload.notes, notes);
    await setNotes(updatedNotes);

    return response(updatedNotes);
  } else {
    return fail("notes is required");
  }
}
