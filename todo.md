# To do list

## Part 1 - content organization

- ✔️ Post status - visible:
  - Feed: return only visible=true.
  - by ID: return only visible=true.
  - `db.posts.updateMany({}, { $set: { visible: true } })`
- ✔️ Token generation by username:password (`token.secret`, `token.issuer`, `token.lifespan`)
- ✔️ Categories.
  - `db.posts.updateMany({}, { $set: { category: "Miscellaneous" } })`
- ✔️ POST/PUT/DELETE posts.
- Add controller and service tests.
- Update frontend to support editing and categories.

## Part 2 - content provision

- RSS feed
  - Separate script to generate a static file?
  - There's no need to generate it dynamically.
  - Maybe spring boot has some built-in cache.
- ActivityPub for subscribers
  - Optional, because I don't really need this.
  - But it'd be interesting to see how the protocol works.
