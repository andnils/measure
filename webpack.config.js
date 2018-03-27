/* global module __dirname */
const path = require('path');
const process = require('process');

const indexHtml = path.join(__dirname, 'src/main/resources', 'index.html');
const OUTPUT_DIR = path.resolve(__dirname, 'dist');
const WEBPACK_DEVSERVER_PORT = 8080;
const API_HOST = process.env.API_HOST ||'localhost';
const API_PORT = 3000;


module.exports = {
  entry: [
    './src/main/js/app.js',
    indexHtml
  ],
  output: {
    path: OUTPUT_DIR,
    filename: 'bundle.js'
  },
  module: {
    rules: [
      {
        test: /\.(s*)css$/,
        use: [
          { loader: 'style-loader' },
          { loader: 'css-loader' },
          { loader: "sass-loader" },
        ]
      },
      {
        test: indexHtml,
        use: [
          {
            loader: 'file-loader',
            options: { name: 'index.html' }
          }
        ]
      },
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: 'babel-loader'
      },
      {
        test: /\.jsx?$/,
        exclude: /node_modules/,
        use: 'babel-loader'
      }
    ]
  },
  devServer: {
    contentBase: OUTPUT_DIR,
    port: WEBPACK_DEVSERVER_PORT,
    proxy: {
      '/api': `http://${API_HOST}:${API_PORT}`
    }
  }
};
