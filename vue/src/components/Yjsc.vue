<template>
  <div>
    <el-container>
      <el-header> </el-header>
      <el-header> CS307 ProjectII </el-header>
      <el-header>
        <el-row>
          <el-col :span="2">
            <el-button type="primary" plain @click="backToHome">Home</el-button>
          </el-col>
        </el-row>
      </el-header>
    </el-container>
    getAllStaffCount:
    <el-table :data="countByType" stripe border style="width: 100%" height="300">
      <el-table-column fixed prop="type" label="type" width="150">
      </el-table-column>
      <el-table-column prop="cnt" label="count" width="150"> </el-table-column>
    </el-table>
    getContractCount:
    <el-table :data="contractCount" stripe border style="width: 100%" height="300">
      <el-table-column fixed prop="0" label="count" width="150">
      </el-table-column>
    </el-table>
    getOrderCount:
    <el-table :data="orderCount" stripe border style="width: 100%" height="300">
      <el-table-column fixed prop="0" label="count" width="150">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data () {
    return {
      countByType: [],
      contractCount: [],
      orderCount: []
    }
  },
  methods: {
    backToHome () {
      this.$router.push('Login')
    },
    CountByType () {
      this.$axios
        .get('/staffs/getAllStaffCount')
        .then((response) => { this.countByType = response.data.data })
    },
    getContractCount () {
      this.$axios
        .get('/contracts/getContractCount')
        .then((response) => { this.contractCount.push(response.data.data) })
    },
    getOrderCount () {
      this.$axios
        .get('/orders/getOrderCount')
        .then((response) => { this.orderCount.push(response.data.data) })
    }
  },
  mounted () {
    this.CountByType()
    this.getContractCount()
  }
}
</script>

<style>
.el-header {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  line-height: 400px;
}

body > .el-container {
  margin-bottom: 40px;
}
</style>
