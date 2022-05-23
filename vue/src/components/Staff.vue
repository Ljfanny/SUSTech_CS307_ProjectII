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
            <el-button type="primary" plain @click="getAll">Staff</el-button>
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
            <el-input v-model="iuage" placeholder="age"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iugender" placeholder="gender"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iunumber" placeholder="number"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iucenter" placeholder="center"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iumn" placeholder="mob number"></el-input>
          </el-col>
          <el-col :span="0.1">
            &#160;
          </el-col>
          <el-col :span="2">
            <el-input v-model="iutype" placeholder="type"></el-input>
          </el-col>
        </el-row>
      </el-header>
    </el-container>
    <el-table :data="tableData" stripe border style="width: 100%" height="500">
      <el-table-column fixed prop="id" label="id" width="150">
      </el-table-column>
      <el-table-column prop="name" label="name" width="150"> </el-table-column>
      <el-table-column prop="age" label="age" width="150"> </el-table-column>
      <el-table-column prop="gender" label="gender" width="150">
      </el-table-column>
      <el-table-column prop="number" label="number" width="150">
      </el-table-column>
      <el-table-column prop="supplyCenter" label="center" width="150">
      </el-table-column>
      <el-table-column prop="mobileNumber" label="mobile_number" width="150">
      </el-table-column>
      <el-table-column prop="type" label="type" width="150"> </el-table-column>
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
      iuage: '',
      iugender: '',
      iunumber: '',
      iucenter: '',
      iumn: '',
      iutype: ''
    }
  },
  methods: {
    backToHome () {
      this.$router.push('Login')
    },
    getAll () {
      this.tableData = []
      this.$axios
        .get('/staffs/all')
        .then((response) => { this.tableData = response.data.data })
    },
    get () {
      this.tableData = []
      this.$axios
        .get('/staffs?' +
        'id=' + this.iuid +
        '&name=' + this.iuname +
        '&age=' + this.iuage +
        '&gender=' + this.iugender +
        '&number=' + this.iunumber +
        '&supplyCenter=' + this.iucenter +
        '&mobileNumber=' + this.iumn +
        '&type=' + this.iutype)
        .then((response) => { this.tableData = response.data.data })
      this.iuid = ''
      this.iuname = ''
      this.iuage = ''
      this.iugender = ''
      this.iunumber = ''
      this.iucenter = ''
      this.iumn = ''
      this.iutype = ''
    },
    dlt () {
      this.tableData = []
      this.$axios.delete('/staffs?' +
        'id=' + this.iuid +
        '&name=' + this.iuname +
        '&age=' + this.iuage +
        '&gender=' + this.iugender +
        '&number=' + this.iunumber +
        '&supplyCenter=' + this.iucenter +
        '&mobileNumber=' + this.iumn +
        '&type=' + this.iutype)
      this.iuid = ''
      this.iuname = ''
      this.iuage = ''
      this.iugender = ''
      this.iunumber = ''
      this.iucenter = ''
      this.iumn = ''
      this.iutype = ''
    },
    insert () {
      this.$axios
        .post('/staffs', {
          id: this.iuid,
          name: this.iuname,
          age: this.iuage,
          gender: this.iugender,
          number: this.iunumber,
          supplyCenter: this.iucenter,
          mobileNumber: this.iumn,
          type: this.iutype
        })
      this.iuid = ''
      this.iuname = ''
      this.iuage = ''
      this.iugender = ''
      this.iunumber = ''
      this.iucenter = ''
      this.iumn = ''
      this.iutype = ''
    },
    update () {
      this.$axios
        .put('/staffs', {
          id: this.iuid,
          name: this.iuname,
          age: this.iuage,
          gender: this.iugender,
          number: this.iunumber,
          supplyCenter: this.iucenter,
          mobileNumber: this.iumn,
          type: this.iutype
        })
      this.iuid = ''
      this.iuname = ''
      this.iuage = ''
      this.iugender = ''
      this.iunumber = ''
      this.iucenter = ''
      this.iumn = ''
      this.iutype = ''
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
