const express = require('express');
const { getItems, addItem, deleteItem, editItem } = require('../controllers/wishlistController');

const router = express.Router();

// Define routes
router.get('/', getItems); // GET /items
router.post('/', addItem); // POST /items
router.delete('/:id', deleteItem);
router.put('/:id', editItem); // PUT /items/:id


module.exports = router;
