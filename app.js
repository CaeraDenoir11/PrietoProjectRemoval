const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');

// Initialize app
const app = express();

// Middleware
app.use(bodyParser.json());
app.use(cors());

// Routes
const wishlistRoutes = require('./routes/wishlistRoutes');
app.use('/items', wishlistRoutes);

// Export app
module.exports = app;
