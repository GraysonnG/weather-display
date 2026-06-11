import { neon } from "@neondatabase/serverless";
import { NEON_CONNECTION_STRING } from "$env/static/private";

/**
 * @typedef {Object} Note
 * @property {string} id
 * @property {number} time_posted
 * @property {string} text
 * @property {string} owner
 * @property {string} color
 */

const sql = neon(NEON_CONNECTION_STRING);

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
 * Gets a record of notes.
 *
 * @returns {Record<string, Note>}
 */
export const getNotes = async () => {
  await ensureTable();
  const notes = await sql`SELECT * FROM notes`;
  const map = {};
  notes.forEach((note) => {
    map[note.id] = note;
  });
  return map;
};

/**
 * Inserts notes into the database.
 *
 * @param {Note[]} notes
 */
export const insertNotes = async (notes) => {
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

/**
 * Deletes notes from the database.
 *
 * @param {Notes[]} notes
 */
export const deleteNotes = async (notes) => {
  await ensureTable();
  for (const note of notes) {
    await sql`DELETE FROM notes WHERE id = ${note.id}`;
  }
};
