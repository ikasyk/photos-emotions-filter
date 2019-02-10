import React, { Component } from "react";

export default class PhotosList extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div id="photos-list">
                {this.props.photosList.map(c => (
                    <figure className="figure col-md-4" key={c.id}>
                        <div className="text-center">
                            <img src={c.imageUrl} className="figure-img img-fluid rounded"/>
                        </div>
                        <figcaption className="figure-caption">{c.label}</figcaption>
                    </figure>
                ))}
            </div>
        );
    }
}