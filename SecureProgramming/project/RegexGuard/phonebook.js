console.log('Started');
// import restAPI from './RestAPI.js';
console.log('Connect to API');
const restAPI = require('./RestAPI');
console.log('Initiate connection to DB');

const call = restAPI();