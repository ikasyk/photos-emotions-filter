import React, {Component} from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import DataRequest from "../DataRequest";
import Filter from "./Filter";
import PhotosList from "./PhotosList";

export default class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            filters: [],
            data: [],
            isProcessed: true
        };

        this.retrieveData();
    }

    retrieveData() {
        const dataReq = new DataRequest();
        dataReq.evaluate(responseData => {
            const data = responseData.photos.map(ph => {
                const imageUrl = ph.sources.find(r => r.label === "Small 320").source;
                return {
                    id: ph.id,
                    label: ph.title,
                    imageUrl: imageUrl,
                    faces: ph.faces
                }
            });
            this.setState({
                data: data,
                isProcessed: false
            });
        });
    }

    render() {
        return (
            <div className="container-fluid">
                <div className="row">
                    <nav className="col-md-2" id="filters-wrap">
                        <Filter onEmotionFilterChange={this.onEmotionChange.bind(this)}/>
                    </nav>
                    <main className="col-md-10" id="photos-wrap">
                        <PhotosList photosList={this.filterEmotions()}/>
                    </main>
                </div>
                <div id="loading-bb" className={this.state.isProcessed ? "d-block" : "d-none"}/>
            </div>
        );
    }

    onEmotionChange(emotion, value) {
        const filters = this.state.filters;
        const filterIndex = filters.indexOf(emotion);

        if (!value) {
            filters.splice(filterIndex, 1);
        } else if(filterIndex === -1) {
            filters.push(emotion);
        }

        this.setState({
            filters: filters
        });
    }

    filterEmotions() {
        if (this.state.filters.length === 0) {
            return this.state.data;
        }
        return this.state.data.filter(photo => {
            return photo.faces.some(f => this.state.filters.indexOf(f) >= 0);
        });
    }
}