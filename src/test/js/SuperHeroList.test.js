import React from 'react';
import renderer from 'react-test-renderer';
import { SuperHeroList } from '../../main/js/SuperHeroList';

const heroes = [
  { id: 1, firstname: 'Bruce', lastname: 'Banner', heroname: 'Hulk' },
  { id: 2, firstname: 'Peter', lastname: 'Parker', heroname: 'Spiderman' }
];

test('test if the super hero list renders same as before', () => {
  const tree = renderer
    .create(<SuperHeroList heroes={heroes}/>)
    .toJSON();
  expect(tree).toMatchSnapshot();
});


const sum = (a, b) => a + b;

test('adds 1 + 2 to equal 3', () => {
  expect(sum(1, 2)).toBe(3);
});
