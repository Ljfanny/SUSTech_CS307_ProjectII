
<template>
    <div>
    <el-page-header @back="goBack" content="Inventory">
    </el-page-header>
      <el-table
    :data="tableData"
    style="width: 100%"
    height="500">
    <el-table-column
      prop="id"
      label="id"
      width="300">
    </el-table-column>
    <el-table-column
      prop="supply_center"
      label="center"
      width="300">
    </el-table-column>
    <el-table-column
      prop="product_model"
      label="model"
      width="300">
    </el-table-column>
    <el-table-column
      prop="supply_staff"
      label="staff"
      width="300">
    </el-table-column>
    <el-table-column
      prop="date"
      label="date"
      width="300">
    </el-table-column>
    <el-table-column
      prop="purchase_price"
      label="price"
      width="300">
    </el-table-column>
        <el-table-column
      prop="surplus_quantity"
      label="surplus quantity"
      width="300">
    </el-table-column>
        <el-table-column
      prop="total_quantity"
      label="total quantity"
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
