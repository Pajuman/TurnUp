// server.js
const express = require("express");
const path = require("path");

const app = express();
const port = process.env.PORT || 3000;

// Serve static files from the Angular dist folder
app.use(express.static(path.join(__dirname, "dist/turnup")));

// Handle all routes by sending back index.html
app.get("*", (req, res) => {
  res.sendFile(path.join(__dirname, "dist/turnup/index.html"));
});

app.listen(port, () => {
  console.log(`App running on port ${port}`);
});
