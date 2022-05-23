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
    <el-header>Staffs count</el-header>
    <el-table :data="countByType" stripe border style="width: 100%" height="500">
      <el-table-column fixed prop="type" label="type" width="150">
      </el-table-column>
      <el-table-column prop="cnt" label="count" width="150"> </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data () {
    return {
      countByType: []
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
    }
  },
  mounted () {
    this.CountByType()
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
