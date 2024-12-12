const mongoose = require('mongoose');
const app = require('./app');

// Connect to MongoDB Atlas
const MONGO_URI = 'mongodb+srv://MarkJosephPrieto:qwerty123@wishlist.v8cqg.mongodb.net/wishlist?retryWrites=true&w=majority&appName=Wishlist';

mongoose.connect(MONGO_URI, { useNewUrlParser: true })
    .then(() => {
        console.log('MongoDB connected');
        const PORT = process.env.PORT || 8000;
        app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
    })
    .catch(err => {
        console.error('Connection error', err);
    });
