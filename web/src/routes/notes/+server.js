import { json } from "@sveltejs/kit";
import { neon } from "@neondatabase/serverless";
import { NOTES_SECRET_KEY, NEON_CONNECTION_STRING } from "$env/static/private";

const sql = neon(NEON_CONNECTION_STRING);

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

const ensureTable = async () => {
  await sql`
    CREATE TABLE IF NOT EXISTS notes (
      id TEXT PRIMARY KEY,
      time_posted BIGINT NOT NULL,
      text TEXT NOT NULL,
      owner TEXT NOT NULL,
      color TEXT NOT NULL
    )
  `;
};

/**
 * @returns {Record<string, Note>}
 */
const getNotes = async () => {
  await ensureTable();
  const rows = await sql`SELECT * FROM notes`;
  const map = {};
  rows.forEach((row) => {
    map[row.id] = row;
  });
  return map;
};

const upsertNotes = async (notes) => {
  await ensureTable();
  for (const note of notes) {
    await sql`
      INSERT INTO notes (id, time_posted, text, owner, color)
      VALUES (${note.id}, ${note.time_posted}, ${note.text}, ${note.owner}, ${note.color})
      ON CONFLICT (id) DO UPDATE SET
        time_posted = EXCLUDED.time_posted,
        text = EXCLUDED.text,
        owner = EXCLUDED.owner,
        color = EXCLUDED.color
    `;
  }
};

const deleteNotes = async (notes) => {
  await ensureTable();
  for (const note of notes) {
    await sql`DELETE FROM notes WHERE id = ${note.id}`;
  }
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
 * @param {Record<string, Note>} notes
 */
const response = (notes) => json({ notes });

export async function GET({ request }) {
  if (!checkAPIKey(request)) return unauthorized();
  const notes = await getNotes();
  return response(notes);
}

/**
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
      add: async (payload_notes) => {
        await upsertNotes(payload_notes);
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
