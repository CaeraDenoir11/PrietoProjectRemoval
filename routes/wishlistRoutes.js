const express = require('express');
const { getItems, addItem } = require('../controllers/wishlistController');

const router = express.Router();

// Define routes
router.get('/', getItems); // GET /items
router.post('/', addItem); // POST /items

module.exports = router;
