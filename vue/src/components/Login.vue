<template>
  <div>
    <el-container>
      <el-header> </el-header>
      <el-header> CS307 ProjectII </el-header>
      <el-header> </el-header>

      <el-header>
        <el-row>
          <el-button type="primary" plain @click="Center">Center</el-button>
          <el-button type="primary" plain @click="Contract">Contract</el-button>
          <el-button type="primary" plain @click="Enterprise"
            >Enterprise</el-button
          >
          <el-button type="primary" plain @click="Inventory"
            >Inventory</el-button
          >
          <el-button type="primary" plain @click="Model">Model</el-button>
          <el-button type="primary" plain @click="Order">Order</el-button>
          <el-button type="primary" plain @click="Staff">Staff</el-button>
        </el-row>
      </el-header>
      <el-header>
        <el-row>
          <el-button type="primary" plain @click="Yjsc">OneKeyOut</el-button>
          <el-button type="primary" plain @click="StaffCount">StaffCount</el-button>
          <el-button type="primary" plain @click="ContractCount">ContractCount</el-button>
          <el-button type="primary" plain @click="OrderCount">OrderCount</el-button>
          <el-button type="primary" plain @click="NeverSold">NeverSold</el-button>
          <el-button type="primary" plain @click="Favorite">Favorite</el-button>
          <el-button type="primary" plain @click="Average">Average</el-button>
        </el-row></el-header
      >
      <el-header>
        <el-row>
          <el-button type="primary" plain @click="Import">Import</el-button>
          <el-button type="primary" plain @click="Product">ProByNum</el-button>
          <el-button type="primary" plain @click="ContractInfo">ContractInfo</el-button>
        </el-row>
         </el-header>
      <el-header> </el-header>
      <el-header> </el-header>
      <el-header> </el-header>
      <el-header> </el-header>
      <el-header> </el-header>
      <el-header> </el-header>
      <el-header> </el-header>
    </el-container>
  </div>
</template>

<script>
export default {
  methods: {
    handleSelect (key, keyPath) {
      console.log(key, keyPath)
    },
    Center: function () {
      this.$router.push('Center')
    },
    Contract: function () {
      this.$router.push('Contract')
    },
    Enterprise: function () {
      this.$router.push('Enterprise')
    },
    Inventory: function () {
      this.$router.push('Inventory')
    },
    Model: function () {
      this.$router.push('Model')
    },
    Order: function () {
      this.$router.push('Order')
    },
    Staff: function () {
      this.$router.push('Staff')
    },
    Yjsc: function () {
      this.$router.push('Yjsc')
    },
    StaffCount: function () {
      this.$router.push('StaffCount')
    },
    ContractCount: function () {
      this.$router.push('ContractCount')
    },
    OrderCount: function () {
      this.$router.push('OrderCount')
    },
    Import: function () {
      this.$router.push('Import')
    },
    NeverSold: function () {
      this.$router.push('NeverSold')
    },
    Favorite: function () {
      this.$router.push('Favorite')
    },
    Average: function () {
      this.$router.push('Average')
    },
    Product: function () {
      this.$router.push('Product')
    },
    ContractInfo: function () {
      this.$router.push('ContractInfo')
    }
  }
}
</script>

<style>
.el-header {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
}

body > .el-container {
  margin-bottom: 40px;
}
</style>
