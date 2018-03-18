import React from 'react';
import PropTypes from 'prop-types';


export const SuperHeroListItem = (props) => {
  const { firstname, lastname } = props.hero;
  return <li>{`${firstname} ${lastname}`}</li>;
};


SuperHeroListItem.propTypes = {
  hero: PropTypes.shape({
    id: PropTypes.number,
    firstname: PropTypes.string,
    lastname: PropTypes.string,
    heroname: PropTypes.string
  })
};
