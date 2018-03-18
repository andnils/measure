module.exports = {
  "env": {
    "browser": true,
    "es6": true,
    "jest": true
  },
  "extends": ["eslint:recommended", "plugin:react/recommended"],
  "parserOptions": {
    "sourceType": "module"
  },
  "rules": {
    "consistent-return": ["error", { "treatUndefinedAsUnspecified": false }],
    "indent": ["error", 2],
    "linebreak-style": ["error", "unix"],
    "no-console": "warn",
    "no-nested-ternary": "error",
    "object-curly-spacing": ["error", "always"],
    "operator-linebreak": ["error", "before"],
    "quotes": ["error", "single"],
    "semi": ["error", "always"]
  }
};
