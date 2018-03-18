import React from 'react';
import PropTypes from 'prop-types';
import { SuperHeroListItem } from './SuperHeroListItem.jsx';


export class SuperHeroList extends React.Component {
  renderItems(heroes) {
    return heroes.map(hero => <SuperHeroListItem key={hero.id} hero={hero}/>);
  }

  render() {
    return (
      <div>
        <h1> Super heroez </h1>
        <ul>
          {this.renderItems(this.props.heroes)}
        </ul>
      </div>
    );
  }
}


SuperHeroList.propTypes = {
  heroes: PropTypes.arrayOf(PropTypes.object)
};

