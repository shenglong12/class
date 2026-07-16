<template>
    <div :class="wrapper_class" v-bind="$attrs">
        <slot />
    </div>
</template>
<script setup>

const { proxy } = getCurrentInstance();

const props = defineProps({
  display: { type: String, default: ''},
  direction: { type: String, default: ''},
  x: { type: String, default: ''},
  y: { type: String, default: ''},
  h_full : { type: Boolean, default: false},
  w_full : { type: Boolean, default: false},
  bg_color: {type: String, default: ''}
});

const wrapper_class = ref('');

watch(
  () => props,
  (newPath, oldPath) => {
    get_wrapper_class();
  },
  { deep: true }
);

function get_wrapper_class(){
    let c = '';
    if(props.h_full){
        c += ' h-full ';
    }
    if( props.w_full){
        c += ' w-full ';
    }

    if(props.display === 'flex'){

        let t = 'flex'

        if(props.direction !=''){
            t+='-'+props.direction;
        }
        else{
            t+='-r';
        }

        if(props.x != ''){
            t+='-'+props.x;
        }
        else{
            t+='-start';
        }

        if(props.y != ''){
            t+='-'+props.y;
        }
        else{
            t+='-start';
        }
        c += ' '+t+' ';
    }

    if(props.bg_color != ''){
        c+=  ' '+props.bg_color+' '; 
    }

    wrapper_class.value = c;
}

get_wrapper_class();

</script>