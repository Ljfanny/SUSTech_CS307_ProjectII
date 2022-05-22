<template>
    <div>
    <el-page-header @back="goBack" content="Center">
    </el-page-header>
      <el-table
    :data="tableData"
    style="width: 100%"
    height="500">
    <el-table-column
      prop="center id"
      label="id"
      width="300">
    </el-table-column>
    <el-table-column
      prop="name"
      label="name"
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
