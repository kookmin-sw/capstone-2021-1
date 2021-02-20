export const MARKERCLICK = 'MARKERCLICK';


export function actionMarkerclick(marker_data) {
    return {
        type: MARKERCLICK,
        marker_data: marker_data
    };
}
