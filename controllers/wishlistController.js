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
// Delete an item
const deleteItem = async (req, res) => {
    const { id } = req.params;

    try {
        const deletedItem = await WishlistItem.findByIdAndDelete(id);

        if (!deletedItem) {
            return res.status(404).json({ message: 'Item not found' });
        }

        res.status(200).json({ message: 'Item deleted successfully', item: deletedItem });
    } catch (error) {
        res.status(500).json({ message: 'Server Error: Unable to delete item', error });
    }
};  
// Edit an item
const editItem = async (req, res) => {
    const { id } = req.params;
    const { name, details, price } = req.body;

    console.log(`Editing item with ID: ${id}`);
    console.log(`Request body:`, req.body);

    if (!name || !price) {
        return res.status(400).json({ message: 'Name and Price are required' });
    }

    try {
        const updatedItem = await WishlistItem.findByIdAndUpdate(
            id,
            { name, details, price },
            { new: true, runValidators: true }
        );

        if (!updatedItem) {
            return res.status(404).json({ message: 'Item not found' });
        }

        res.status(200).json({ message: 'Item updated successfully', item: updatedItem });
    } catch (error) {
        console.error(`Error updating item: ${error.message}`);
        res.status(500).json({ message: 'Server Error: Unable to update item', error });
    }
};
// Export functions
module.exports = {
    getItems,
    addItem,
    deleteItem,
    editItem,
};
