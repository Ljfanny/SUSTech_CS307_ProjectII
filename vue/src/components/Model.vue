<template>
    <div>
    <el-page-header @back="goBack" content=" Model">
    </el-page-header>
      <el-table
    :data="tableData"
    style="width: 100%"
    height="500">
    <el-table-column
      prop="model_id"
      label="id"
      width="300">
    </el-table-column>
    <el-table-column
      prop="number"
      label="number"
      width="300">
    </el-table-column>
    <el-table-column
      prop="model"
      label="model"
      width="300">
    </el-table-column>
    <el-table-column
      prop="name"
      label="name"
      width="300">
    </el-table-column>
    <el-table-column
      prop="unit_price"
      label="price"
      width="300">
    </el-table-column>
  </el-table>
    </div>
</template>

<script>
export default {
  methods: {
    goBack: function () {
      this.$router.push('Login')
    }
  }
}
</script>
