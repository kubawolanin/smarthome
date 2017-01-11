---
layout: documentation
---

{% include base.html %}

# Air Quality Binding

This binding uses the [AQIcn.org service](http://aqicn.org/api/) for providing air quality information for any location worldwide.

The World Air Quality Index project is provided by The Weather Underground, LLC (WUL) free of charge but there is a daily limit and minute rate limit to the number of requests that can be made to the API for free. You may exceed this threshold only if you are or become a fee paying subscriber. WUL will monitor your daily usage of the API to determine if you have exceeded the free-use threshold by using an API key. By using this binding, you confirm that you agree with the [Weather Underground API terms and conditions of use](https://www.wunderground.com/weather/api/d/terms.html).

To use this binding, you first need to [register and get your API token](http://aqicn.org/data-platform/token/#/).
After providing your email and name, you'll need to confirm registration by clicking a URL provided in the email message from AQI.
Then you will be able to access your API token.

## Supported Things

There is exactly one supported thing type, which represents the air quality information for an observation location. It has the id `aqi`.

## Discovery

There is no discovery implemented. You have to create your things manually.

## Binding Configuration
 
The binding has no configuration options, all configuration is done at Thing level.
 
## Thing Configuration

The thing has a few configuration parameters:

| Parameter | Description                                                              |
|-----------|------------------------------------------------------------------------- |
| apikey    | Data-platform token to access the AQIcn.org service. Mandatory. |
| location  | Geo coordinates to be considered by the service. Mandatory. |
| refresh   | Refresh interval in minutes. Optional, the default value is 30 minutes.  |

For the location parameter, the following syntax is allowed:

`37.8,-122.4`

## Channels

The AQI information that is retrieved is available as these channels:

| Channel ID | Item Type    | Description              |
|------------|--------------|------------------------- |
| aqi | Number | Air Quality Index | // aqi
| aqiDescription | String | AQI Description | // aqi mapped
| pm25 | Number | Fine particles pollution level (PM2.5) | // iaqi.pm25.v
| pm10 | Number | Coarse dust particles pollution level (PM10) | // iaqi.pm10.v
| no2 | Number | Nitrogen Dioxide level (NO2) | // iaqi.no2.v
| co | Number | Carbon monoxide level (CO) | // iaqi.co.v
| locationName | String | Nearest measuring station location | // city.name
| locationUrl | String | Direct URL to the station results | // city.url
| observationTime | DateTime | Observation date and time | // time.s
| stationId | Number | Measurement station identifier |   // idx
| temperature | Number | Temperature in Celsius degrees | // iaqi.t.v
| pressure | Number | Pressure level | // iaqi.p.v
| humidity | Number | Humidity level | // iaqi.h.v


```
good
moderate
unhealthy-for-sensitive-groups
unhealthy
very-unhealthy
hazardous
```

airquality.scale
```
-=No data
[0..50]=Good
[51..100]=Moderate
[101..150]=Unhealthy for Sensitive Groups
[151..200]=Unhealthy
[201..300]=Very Unhealthy
[300..]=Hazardous
```

## Full Example

demo.things:

```
Thing airquality:aqi:Krakow "AirQuality in Krakow" [ apikey="XXXXXXXXXXXX", location="50.06465,19.94498" ]
```

demo.items:

```
TBD
```

demo.sitemap:

```
TBD
```
