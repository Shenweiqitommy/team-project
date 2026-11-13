# Travel Planner & Journal

Keep this README current. Update it as part of every PR so the final presentation is easier to prepare.

## Project domain

**Travel.** A desktop app (Java Swing) for planning a trip and keeping a journal while on it.

- **Before the trip:** plan the trip (forecast, currency conversion, and more). _Teammates: fill in your features._
- **During the trip:** write a daily journal with photos, take notes, and save memories to revisit later.

## User stories

One story per team member.

| User story | Owner |
| --- | --- |
| As a traveller, I want to write a journal entry for each day of my trip and attach up to 3 photos, so I can remember what I did. | Jeff Tian |
| _To be added_ | |

## Use cases

### Write daily journal entry (MVP)

**Lead:** Jeff Tian

- **Actor:** Traveller
- **Precondition:** A trip exists.
- **Main flow:**
  1. The user opens the journal and picks a date.
  2. The user writes the entry and optionally attaches up to 3 photos (jpg, jpeg, png, gif).
  3. The user clicks Save.
  4. The system copies the photos into app storage, saves the entry, and confirms with "Saved your entry for <date>."
- **Alternative flows:**
  - The text is empty: show "Write something before saving."
  - The user picks a 4th photo: the Add photo button is disabled; the interactor also rejects more than 3.
  - A file is not a supported image: show "<file> is not a supported image."
  - A photo cannot be copied: show "Could not save photo <file>."
  - The date already has an entry: replace it and show "Updated your entry for <date>."
- **Postcondition:** The entry and its photos are saved and are still there after restarting the app.
- **Not yet done:** checking that the date falls within the trip (needs the Trip entity), and selecting a real trip (uses a placeholder trip id for now).

_Teammates: add your use cases here, marked MVP or later, with the lead's name._

## Entities

- **JournalEntry:** `tripId`, `date`, `text`, `photos` (at most 3). Methods: `addPhoto`, `removePhoto`, `canAddPhoto`, `setText` (rejects blank text).
- **Photo:** `filePath`.
- _To be added: Trip and the other entities._

## API usage

_To be added: link to each API's documentation, how the project uses it, and whether the team has called it successfully._

## Persistence

Journal entries are stored in `data/journal.json`. Attached photos are copied into `data/photos/`. The `data/` folder is created at runtime and is not committed.

## Running the project

Requires JDK 11 or newer and Maven.

- Run the app: `mvn exec:java`
- Run the tests: `mvn test`

## Screenshots

_Add screenshots or animations as features land._
