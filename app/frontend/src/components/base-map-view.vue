<template>
  <map
      ref="mapRef"
      class="w-full"
      :latitude="latitude"
      :longitude="longitude"
      min-scale="3"
      max-scale="20"
      :markers="markers"
      :polyline="polyline"
      :show-location="show_location"
      @tap="tap"
      @markertap="onMarkerTap"
  />
</template>

<script setup>
const {proxy} = getCurrentInstance()
const props = defineProps({
  show_location: {
    type: Boolean,
    default: true
  },
  latitude: {
    type: Number,
    required: true
  },
  longitude: {
    type: Number,
    required: true
  },
  scale: {
    type: Number,
    default: 14
  },
  markers: {
    type: Array,
    default: () => []
  },
  polyline: {
    type: Array,
    default: () => []
  }
})

const emit = defineEmits(['markerTapClick', 'mapTap'])

function onMarkerTap(e) {
  emit('markerTapClick', e.detail)
}

function tap(e) {
  console.log(e, "tap")
  if (isEmpty(e.detail)) {
    e.detail.latitude = props.latitude
    e.detail.longitude = props.longitude
  }
  console.log(e.detail, "tap detail")
  emit('mapTap', {
    latitude: e.detail.latitude,
    longitude: e.detail.longitude
  })
}

const isEmpty = (obj) => Object.keys(obj).length === 0 && obj.constructor === Object;

</script>