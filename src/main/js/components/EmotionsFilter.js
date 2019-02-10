import React, {Component} from "react";

const emotions = {
    sadness: "Sadness",
    neutral: "Neutral",
    disgust: "Disgust",
    anger: "Anger",
    surprise: "Surprise",
    fear: "Fear",
    happiness: "Happiness"
};

export default class EmotionsFilter extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const list = Object.keys(emotions).map(e => (
            <div className="form-check" key={e}>
                <input type="checkbox" className="form-check-input" value={e} id={"emotion-" + e}
                       name="emotionsFilter"
                       onChange={this.onEmotionChange(e)}/>
                <label htmlFor={"emotion-" + e}>{emotions[e]}</label>
            </div>
        ));
        return (
            <div id="emotions-filter">
                <h4>Emotions</h4>
                {list}
            </div>
        );
    }

    onEmotionChange(emotion) {
        const onChange = this.props.onEmotionChange;
        return event => {
            onChange(emotion, event.target.checked);
        }
    }
}