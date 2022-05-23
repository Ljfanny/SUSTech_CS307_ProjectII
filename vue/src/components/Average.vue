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
            <el-header>Average</el-header>
    <el-table :data="avg" stripe border style="width: 100%" height="700">
      <el-table-column fixed prop="supplyCenter" label="center" width="300">
      </el-table-column>
      <el-table-column prop="average" label="average" width="150"> </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data () {
    return {
      countByType: [],
      contractCount: [],
      orderCount: [],
      nSPC: [],
      favorite: [],
      avg: []
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
        .then((response) => {
          this.orderCount.push(response.data.data)
        })
    },
    getNeverSoldProductCount () {
      this.$axios
        .get('/inventories/getNeverSoldProductCount')
        .then((response) => {
          this.nSPC.push(response.data.data)
        })
    },
    getFavoriteProductModel () {
      this.$axios
        .get('/orders/getFavoriteProductModel')
        .then((response) => {
          this.favorite = response.data.data
        })
    },
    getAvgStockByCenter () {
      this.$axios
        .get('/orders/getAvgStockByCenter')
        .then((response) => {
          this.avg = response.data.data
        })
    }
  },
  mounted () {
    this.CountByType()
    this.getContractCount()
    this.getOrderCount()
    this.getNeverSoldProductCount()
    this.getFavoriteProductModel()
    this.getAvgStockByCenter()
  }
}
</script>

<style>
.el-header {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  line-height: 60px;
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
