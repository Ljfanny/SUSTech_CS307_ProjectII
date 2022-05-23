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
          <el-col :span="2">
            <el-button type="primary" plain @click="getAll">Enterprise</el-button>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" plain @click="get">select</el-button>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" plain @click="dlt">delete</el-button>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" plain @click="insert">insert</el-button>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" plain @click="update">update</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-header>
        <el-row>
          <el-col :span="2">
            <el-input v-model="iuid" placeholder="id"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iuname" placeholder="name"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iucountry" placeholder="country"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iucity" placeholder="city"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iusc" placeholder="sup center"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iuindustry" placeholder="industry"></el-input>
          </el-col>
        </el-row>
      </el-header>
    </el-container>
    <el-table :data="tableData" stripe border style="width: 100%" height="500">
      <el-table-column fixed prop="id" label="id" width="150">
      </el-table-column>
      <el-table-column prop="name" label="name" width="150"> </el-table-column>
      <el-table-column prop="country" label="country" width="150"> </el-table-column>
      <el-table-column prop="city" label="city" width="150">
      </el-table-column>
      <el-table-column prop="supplyCenter" label="supply center" width="150">
      </el-table-column>
      <el-table-column prop="industry" label="industry" width="150">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data () {
    return {
      tableData: [],
      iuid: '',
      iuname: '',
      iucountry: '',
      iucity: '',
      iusc: '',
      iuindustry: ''
    }
  },
  methods: {
    backToHome () {
      this.$router.push('Login')
    },
    getAll () {
      this.tableData = []
      this.$axios
        .get('/enterprises/all')
        .then((response) => { this.tableData = response.data.data })
    },
    get () {
      this.tableData = []
      this.$axios
        .get('/enterprises?' +
        'id=' + this.iuid +
        '&name=' + this.iuname +
        '&country=' + this.iucountry +
        '&city=' + this.iucity +
        '&supplyCenter=' + this.iusc +
        '&iuindustry=' + this.iuindustry)
        .then((response) => { this.tableData = response.data.data })
      this.iuid = ''
      this.iuname = ''
      this.iucountry = ''
      this.iucity = ''
      this.iusc = ''
      this.iuindustry = ''
    },
    dlt () {
      this.tableData = []
      this.$axios.delete('/enterprises?' +
        'id=' + this.iuid +
        '&name=' + this.iuname +
        '&country=' + this.iucountry +
        '&city=' + this.iucity +
        '&supplyCenter=' + this.iusc +
        '&iuindustry=' + this.iuindustry)
      this.iuid = ''
      this.iuname = ''
      this.iucountry = ''
      this.iucity = ''
      this.iusc = ''
      this.iuindustry = ''
    },
    insert () {
      this.$axios
        .post('/enterprises', {
          id: this.iuid,
          name: this.iuname,
          country: this.iucountry,
          city: this.iucity,
          supplyCenter: this.iusc,
          industry: this.iuindustry
        })
      this.iuid = ''
      this.iuname = ''
      this.iucountry = ''
      this.iucity = ''
      this.iusc = ''
      this.iuindustry = ''
    },
    update () {
      this.$axios
        .put('/enterprises', {
          id: this.iuid,
          name: this.iuname,
          country: this.iucountry,
          city: this.iucity,
          supplyCenter: this.iusc,
          industry: this.iuindustry
        })
      this.iuid = ''
      this.iuname = ''
      this.iucountry = ''
      this.iucity = ''
      this.iusc = ''
      this.iuindustry = ''
    }
  },
  mounted () {
    this.getAll()
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
