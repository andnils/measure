import React from 'react';
import { SuperHeroList } from './SuperHeroList.jsx';


export class AppContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      heroes: []
    };
  }
  
  componentDidMount() {
    this.fetchHeroes();
  }

  fetchHeroes() {
    fetch('/api/heroes')
      .then((response) => response.ok ? response.json() : [])
      .then((data) => this.setState({ heroes: data }));
  }

  render() {
    const { heroes } = this.state;
    return (
      <div>
        <SuperHeroList heroes={heroes}/>
        { !heroes.length && <p> No data </p> }
      </div>
    );
  }
}

