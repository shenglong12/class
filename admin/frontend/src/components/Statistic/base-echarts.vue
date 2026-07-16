<template>
  <view class="all_echarts">
    <view style="text-align: left;color: rgba(0,0,0,1);
font-size: 28rpx;
text-align: left;
font-family: SourceHanSansSC-regular;">
      {{ chartName }}
    </view>

    <!-- <base-ec-canvas
      class="uni-ec-canvas"
      id="line-chart"
      ref="canvasDom"
      canvas-id="lazy-load-chart"
      :ec="ec"
    ></base-ec-canvas> -->

    <view  v-if="is_echarts">
      <div class="chart" ref="chartDom"></div>
    </view>
    <view v-else class="content">
      <view v-for="item in option" :key="item.name">
        <view v-if="item.children && item.children.length > 0" class="option-list">
          <base-echarts :option="item.children"/>
        </view>
        <view v-else>
          <view style="display: flex; flex-direction: column; align-items: flex-start; justify-content: flex-start;">
            <view class="option-text" v-if="item.name">
              <image :src="get_resource_url('static/user.png')" style="width: 0.9rem; height:0.9rem;" mode="heightFix"/>
              <view style="flex:1;font-weight: 400;">
                {{ item.name }}
              </view>
            </view>
            <view class="option-value">
              <image :src="get_resource_url('static/cont_text.png')" style="width: 0.9rem; height:0.9rem;"
                     mode="heightFix"/>
              <view style="flex:1;font-weight: 400;">{{ item.value }}</view>
            </view>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import * as echarts from 'echarts';
// import * as echarts from './l-echart/echart.min.js';
// const echarts = require('./l-echart/echart.min.js') ;
// const echarts = require('./echarts-uniapp/echarts.min.js') ;
// import * as echarts from './uni-ec-canvas/echarts.js'
const props = defineProps({
  option: {
    type: Array,
    default: () => []
  },
  group_result_show_display: {
    type: String,
    default: "text"
  },
  chartName: {
    type: String,
    default: "数据来源"
  }
  // charType: {
  //   type: String,
  //   default: "pie"
  // }
});

const is_echarts = ref(true);
const myChart = ref(null); // 保存图表实例
const chartDom = ref(null); // 使用 ref 直接获取 DOM 元素
const canvasDom = ref(null); // 使用 ref 直接获取 DOM 元素

const ec = ref({
  lazyLoad: true,
  options: {} //echart 配置项
})

const option = {
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    },
    confine: true
  },
  legend: {
    data: ['热度', '正面', '负面']
  },
  grid: {
    left: 20,
    right: 20,
    bottom: 15,
    top: 40,
    containLabel: true
  },
  xAxis: [{
    type: 'value',
    axisLine: {
      lineStyle: {
        color: '#999999'
      }
    },
    axisLabel: {
      color: '#666666'
    }
  }],
  yAxis: [{
    type: 'category',
    axisTick: {
      show: false
    },
    data: ['汽车之家', '今日头条', '百度贴吧', '一点资讯', '微信', '微博', '知乎'],
    axisLine: {
      lineStyle: {
        color: '#999999'
      }
    },
    axisLabel: {
      color: '#666666'
    }
  }],
  series: [{
    name: '热度',
    type: 'bar',
    label: {
      normal: {
        show: true,
        position: 'inside'
      }
    },
    data: [300, 270, 340, 344, 300, 320, 310],
  },
    {
      name: '正面',
      type: 'bar',
      stack: '总量',
      label: {
        normal: {
          show: true
        }
      },
      data: [120, 102, 141, 174, 190, 250, 220]
    },
    {
      name: '负面',
      type: 'bar',
      stack: '总量',
      label: {
        normal: {
          show: true,
          position: 'left'
        }
      },
      data: [-20, -32, -21, -34, -90, -130, -110]
    }
  ]
};

const optionObj = ref({})

// 判断是否展示图表还是文本
const is_echarts_function = () => {
  is_echarts.value = props.group_result_show_display !== 'text';
};

is_echarts_function();

const initMiniChart = (canvas, width, height, canvasDpr) => {
  console.log(canvas, width, height, canvasDpr)
  chart = miniEcharts.init(canvas, null, {
    width: width,
    height: height,
    devicePixelRatio: canvasDpr
  })
  canvas.setChart(chart)
  chart.setOption(this.option)
  return chart
}

// 初始化图表
const initChart = () => {
  if (!chartDom.value) return; // 确保 DOM 已经挂载
  myChart.value = echarts.init(chartDom.value); // 使用 ref 获取 DOM 元素
  myChart.value.setOption(getChartOption());
  window.addEventListener('resize', () => {
    if (myChart.value) {
      myChart.value.resize();
    }
  });
};

// 当组件挂载时初始化图表
onMounted(() => {
  console.log('t888', canvasDom)

  // #ifdef MP-WEIXIN
  setTimeout(async () => {
    if (!canvasDom.value) return
    myChart.value = await canvasDom.value.init(echarts)
    myChart.value.setOption(getChartOption())
  }, 300)
  // #endif

  if (is_echarts.value) {
    initChart();
  }
});

// 监听 option prop 变化，重新渲染图表
watch(() => props.option, (newVal) => {
  if (is_echarts.value && myChart.value) {
    myChart.value.setOption({
      series: [{
        data: newVal
      }]
    });
  }
}, {
  deep: true
});

// 组件卸载时移除事件监听
onBeforeUnmount(() => {
  if (myChart.value) {
    myChart.value.dispose();
  }
});

const getChartOption = () => {
  if (props.group_result_show_display === 'pie' || props.group_result_show_display === 'chart') {
    return {
      tooltip: {
        trigger: 'item',
        formatter: '{b}: {c} ({d}%)', // 显示名称、数值和百分比
      },
      // tooltip: { trigger: 'item' },
      legend: {
        type: 'scroll', // 启用滚动
        orient: 'horizontal', // 水平排列
        bottom: '10%',
        left: 'center',
        icon: 'circle',
        itemWidth: 40, // 图例图标的宽度
        itemGap: 10, // 图例项之间的间距
        pageIconSize: 15, // 控制分页图标的大小
        pageButtonItemGap: 5, // 分页图标与图例项之间的间距
        formatter: function (name) {
          return name.length > 20 ? name.substr(0, 5) + '...' : name;
        },
      },
      series: [{
        name: props.chartName,
        type: 'pie',
        radius: ['40%', '60%'],
        center: ['50%', '50%'],
        avoidLabelOverlap: true,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'outside',
          formatter: '{b|{b}: }{c}',
          rich: {
            b: {
              width: 200,
              overflow: 'break'
            },
            c: {
              width: 50,
              align: 'right'
            }
          }
        },
        emphasis: {
          label: {
            show: false,
            fontSize: 20,
            fontWeight: 'bold',
            formatter: '{b}: {c} ({d}%)'
          }
        },
        labelLine: {
          show: true
        },
        data: props.option
      }]
    };
  } else if (props.group_result_show_display === 'bar') {
    return {
      tooltip: {
        trigger: 'item',
        formatter: '{b}: {c}', // 显示名称、数值和百分比
      },
      legend: {
        type: 'scroll', // 启用滚动
        orient: 'horizontal', // 水平排列
        top: '2%',
        left: 'center',
        itemWidth: 40, // 图例图标的宽度
        itemGap: 50, // 图例项之间的间距
        pageIconSize: 15, // 控制分页图标的大小
        pageButtonItemGap: 5, // 分页图标与图例项之间的间距
        formatter: function (name) {
          return name.length > 9 ? name.substr(0, 5) + '...' : name;
        },
      },
      xAxis: {
        type: 'category',
        data: props.option.map(item => item.name), // 假设数据是通过 props.option 传递的，且包含 name 属性
        axisLabel: {
          interval: 0, // 显示所有 x 轴标签
          rotate: 30, // 标签倾斜角度
        }
      },
      yAxis: {
        type: 'value',
      },
      series: [{
        name: props.chartName,
        type: 'bar', // 条形图
        data: props.option.map((item, index) => ({
          value: item.value,
          itemStyle: {
            borderColor: '#fff',
            borderWidth: 1,
            // color: ['#ff7f50', '#87cefa', '#32cd32', '#ff6347', '#8a2be2', '#ff1493'][index % 6] // 使用颜色数组循环应用
          }
        })), // 假设数据包含 value 属性

        barWidth: "20%",
      }]
    };
  } else if (props.group_result_show_display === 'line') {
    return {
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'line', // 坐标轴指示器类型
          lineStyle: {
            color: '#999', // 指示器颜色
            width: 1, // 指示器宽度
            type: 'dashed', // 指示器类型为虚线
          },
        },
        formatter: '{b}<br/>{a0}: {c0}<br/>{a1}: {c1}', // 格式化显示内容
      },
      legend: {
        type: 'scroll', // 启用滚动
        orient: 'horizontal', // 水平排列
        top: '5%',
        left: 'center',
        itemWidth: 40, // 图例图标的宽度
        itemGap: 10, // 图例项之间的间距
      },
      xAxis: {
        type: 'category', // X 轴为类别轴
        boundaryGap: false, // 不留白，保证线条连接
        data: props.option.map(item => item.name), // 假设数据是通过 props.option 传递的，包含 name 属性
        axisLabel: {
          interval: 0, // 显示所有 X 轴标签
          rotate: 30, // 标签旋转角度
        },
      },
      yAxis: {
        type: 'value', // Y 轴为值轴
      },
      series: [{
        name: props.chartName, // 系列名
        type: 'line', // 折线图
        data: props.option.map(item => item.value), // 假设数据包含 value 属性
        smooth: true, // 是否平滑曲线
        itemStyle: {
          color: '#ff7f50', // 线条颜色
        },
        lineStyle: {
          width: 2, // 线条宽度
        },
        symbol: 'circle', // 数据点标记形状
        symbolSize: 8, // 数据点大小
        emphasis: {
          focus: 'series', // 鼠标悬停时，聚焦于系列
          itemStyle: {
            color: '#ff6347', // 鼠标悬停时，数据点的颜色
          },
        },
      }],
    };
  }

}
</script>
<style scoped>
.all_echarts {
  /* width: 100%; */
  width: 40vw;
  max-width: 100%;
  overflow: hidden;
}

.option-list {
  margin-bottom: 1rem;
  padding-bottom: 1rem;
  padding: 10px;
  border-bottom: 0.8px solid rgba(229, 229, 229, 1);

}

.option-text {
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
  margin-bottom: 0.2rem;
  color: rgba(113, 142, 191, 1);
  font-size: 0.95rem;
}

.option-text image {
  margin-right: 0.7rem;
  margin-top: 0.2rem;
}

.option-value {
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
  font-size: 0.95rem;
}

.option-value image {
  margin-right: 0.4rem;
  margin-top: 0.2rem;
}

.chart {
  width: 100%;
  /* 确保容器宽度为百分比，支持响应式 */
  height: 50vh;
  /* 容器高度 */
  margin: 0 auto;
}

.chart {
  width: 40vw !important;
}

.uni-ec-canvas {
  width: 90vw !important;
  /* 确保容器宽度为百分比，支持响应式 */
  height: 500rpx;
  /* 容器高度 */
  margin: 0 auto;
}
</style>