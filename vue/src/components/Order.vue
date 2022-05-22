<template>
  <div>
    <el-page-header @back="goBack" content="Order"> </el-page-header>
    <el-table :data="tableData" style="width: 100%" height="500">
      <el-table-column prop="id" label="id" width="300"> </el-table-column>
      <el-table-column prop="contract_number" label="number" width="300">
      </el-table-column>
      <el-table-column prop="enterprise" label="enterprise" width="300">
      </el-table-column>
      <el-table-column prop="product_model" label="model" width="300">
      </el-table-column>
      <el-table-column prop="quantity" label="quantity" width="300">
      </el-table-column>
      <el-table-column prop="contract_manager" label="manager" width="300">
      </el-table-column>
      <el-table-column prop="contract_date" label="contract date" width="300">
      </el-table-column>
      <el-table-column
        prop="estimated_delivery_date"
        label="estimated delivery date"
        width="300"
      >
      </el-table-column>
      <el-table-column prop="lodgement_date" label="lodgement date" width="300">
      </el-table-column>
      <el-table-column
        prop="salesman_number"
        label="salesman number"
        width="300"
      >
      </el-table-column>
      <el-table-column
        prop="contract_type"
        label="contract type"
        width="300"
      >
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
