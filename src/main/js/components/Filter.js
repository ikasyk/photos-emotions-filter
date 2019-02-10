import React, { Component } from "react";
import EmotionsFilter from "./EmotionsFilter";

export default class Filter extends Component {
    render() {
        return (
            <div id="filters">
                <EmotionsFilter onEmotionChange={this.props.onEmotionFilterChange}/>
            </div>
        );
    }
}