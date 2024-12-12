const mongoose = require('mongoose');

// Define WishlistItem Schema
const WishlistItemSchema = new mongoose.Schema({
    name: { type: String, required: true },
    details: { type: String, required: true },
    price: { type: String, required: true },
});

// Export the model
module.exports = mongoose.model('WishlistItem', WishlistItemSchema);
