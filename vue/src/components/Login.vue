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
        </el-row></el-header
      >
      <el-header>
        <el-row>
          <el-button type="primary" plain @click="Import">Import</el-button>
        </el-row>
         </el-header>
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
    Import: function () {
      this.$router.push('Import')
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
