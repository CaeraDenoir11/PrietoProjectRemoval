const WishlistItem = require('../models/WishlistItem');

// Get all items
const getItems = async (req, res) => {
    try {
        const items = await WishlistItem.find();
        res.status(200).json(items);
    } catch (error) {
        res.status(500).json({ message: 'Server Error: Unable to fetch items', error });
    }
};

// Add a new item
const addItem = async (req, res) => {
    const { name, details, price } = req.body;

    if (!name || !details || !price) {
        return res.status(400).json({ message: 'All fields are required' });
    }

    try {
        const newItem = new WishlistItem({ name, details, price });
        await newItem.save();
        res.status(201).json({ message: 'Item added successfully', item: newItem });
    } catch (error) {
        res.status(500).json({ message: 'Server Error: Unable to add item', error });
    }
};

// Export functions
module.exports = {
    getItems,
    addItem,
};
