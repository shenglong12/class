<template>
  <view class="all">


    <view v-if="!is_group">
      <!--    不分组执行的情况-->

      <base-echarts v-if="questions.length > 0" :option="questions"
                    :group_result_show_display="group_result_show_display" :chartName="title"></base-echarts>

      <uni-section v-else class="custom-section" :title="title" :sub-title="number"></uni-section>
    </view>

    <view v-if="is_group">

      <!--  分组执行的情况 -->
      <view v-for="(question, index) in questions" :key="index" class="question-item">
        <view class="shu">
          <img :src="get_resource_url('static/shu.png')" style="width:5px;height:18px;margin-right:7px" alt="">
          <text class="question-text">{{ question.name }}</text>
        </view>
        <view v-if="question.children && question.children.length > 0" class="option-list">
          <base-echarts :option="question.children" :inx="index"
                        :group_result_show_display="group_result_show_display" :chartName="title"/>
          <!--                <view v-for="(option, idx) in question.children" :key="idx" class="option-item">-->
          <!--                  <text class="option-text" v-if="option.name">{{ option.name }} -</text>-->
          <!--                  <text class="option-value">{{ option.value }}</text>-->
          <!--                </view>-->
        </view>
      </view>

    </view>
  </view>

</template>
<script>
export default {
  options: {
    styleIsolation: 'shared', // 解除样式隔离
  }
};
</script>
<script setup>
import get_resource_url from '../../config/static_config';

const { proxy } = getCurrentInstance();
const props = defineProps({
  title: {
    type: String,
    default: '',
  },
  is_group: {
    type: Boolean,
    default: false,
  },
  api: {
    type: String,
    default: '',
  },
  model: {
    type: String,
    default: '',
  },
  group_condition_list: {
    type: String,
    default: '',
  },
  field: {
    type: String,
    default: '',
  },
  params: {
    type: Object, default: () => {
    }
  },
  group_result_show_display: {
    type: String,
    default: "text"
  }
});

const number = ref("0")


// 获取question
const questions = ref([]
);

const get_question_url = async () => {
  if (props.is_group) {
    let data = {}
    if (props.params) {
      Object.assign(data, props.params);
    }
    var res = await proxy.$api[props.api][props.model](data)
    questions.value = res.data
  }
}
onMounted(async () => {
  let data = {}
  if (props.params) {
    Object.assign(data, props.params);
  }

  if (!props.is_group) {

    if(import.meta.env.VITE_APP_MODEL === 'PREVIEW'){
      data['field'] = props.field;
      data['group_condition_list'] = props.group_condition_list;

      var res = await proxy.$api.statistic[props.model]({
        table_name: props.api,
        param: data
      })
      if (res.data != null) {
        if (Array.isArray(res.data)) {
          questions.value = res.data
        } else {
          number.value = String(res.data);
        }
      }
    }
    else{
      var res = await proxy.$api[props.api][props.model](data)
      if (res.data != null) {
        if (Array.isArray(res.data)) {
          questions.value = res.data
        } else {
          number.value = String(res.data);
        }
      }
    }
  } else {
    get_question_url()
  }


})


</script>

<style scoped>
.all {
  padding:30px 10px;
  width: 48% !important;
}

.custom-section {
  /*width: 48%;*/
 /* margin-bottom: 10px; */
  /* padding: 15px; */
  /* border-radius: 8px; */
  background-color: white;
  /* box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1); */
  text-align: center;
  /* background: linear-gradient(to bottom, rgb(93, 95, 239), white); */
}

.custom-section::after {
  content: ''; /* 清除浮动 */
  display: table;
  clear: both;
}

.uni-section__title {
  font-size: 18px;
  font-weight: bold;
  color: #5D5FEF; /* 使用首选颜色 */
  margin-bottom: 8px;
}

::v-deep .uni-section-header__decoration {
  width: 0px;
  margin-right: 0px;
  background-color: white;
}

.uni-section__sub-title {
  font-size: 24px;
  color: #333;
}


.question-item {
  margin-bottom: 20px;
}

.shu {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
}

.question-text {
  font-weight: 400;
  font-size: 1.3rem;
}

.option-list {
  /*margin-left: 20px;*/
  /* padding: 10px; */
}

</style>
