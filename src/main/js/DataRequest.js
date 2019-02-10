import jQuery from "jquery";

export default class DataRequest {
    constructor() {
    }

    evaluate(callback) {
        jQuery.ajax({
            url: "/api/getPhotos",
            data: {
                photoSetId: "72157674388093532",
                userId: "144522605@N06",
                tag: "int20h"
            },
            dataType: "json"
        }).done(response => {
            callback(response);
        });
    }
}