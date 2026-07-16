const modulesFiles = import.meta.glob('./*/*.*', {import: 'default', eager: true});

const modules = {};
for (const key in modulesFiles) {
  const moduleName = key.replace(/(.*\/)*([^.]+).*/gi, '$2');
  modules[moduleName] = modulesFiles[key];
}

export default modules;




//
//
// // 全局加载所有目录下的 js 文件
// const modulesFiles = import.meta.glob('./**/*.js', { import: 'default', eager: true });
//
// // 工具方法：将路径拆分为嵌套结构
// function setDeepObject(obj, pathArray, value) {
//   let current = obj;
//   //  找到最内层的数据
//   for (let i = 0; i < pathArray.length - 1; i++) {
//     const key = pathArray[i];
//     if (!current[key]) current[key] = {};
//     current = current[key];
//   }
//   current[pathArray[pathArray.length - 1]] = value;
// }
//
// const cf_modules = {};
// for (const key in modulesFiles) {
//   // key 示例: './cf/table/table.js'
//   const cleanedPath = key.replace(/^\.\/|\.js$/g, ''); // -> 'cf/table/insert'
//   const pathArray = cleanedPath.split('/');            // -> ['cf', 'table', 'insert']
//   setDeepObject(cf_modules, pathArray, modulesFiles[key]);
// }
//
// export default cf_modules;